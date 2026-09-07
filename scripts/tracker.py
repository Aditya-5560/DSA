#!/usr/bin/env python3
import os
import re
import json
import difflib
from datetime import datetime
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent.parent
DATA_FILE = BASE_DIR / "scripts" / "data" / "apna_college_sheet.json"
README_FILE = BASE_DIR / "README.md"
LEETCODE_DIR = BASE_DIR / "Leetcode"
GFG_DIR = BASE_DIR / "GFG"

# Code extensions to recognize
CODE_EXTENSIONS = {
    ".java": "Java",
    ".cpp": "C++",
    ".c": "C",
    ".py": "Python",
    ".js": "JavaScript",
    ".ts": "TypeScript",
    ".go": "Go",
    ".rs": "Rust",
    ".cs": "C#",
    ".kt": "Kotlin",
    ".swift": "Swift",
    ".rb": "Ruby",
    ".txt": "Code"
}

def normalize(text):
    if not text:
        return ""
    text = text.lower()
    text = re.sub(r'[^a-z0-9]', '', text)
    return text

def make_slug(text):
    text = text.lower()
    text = re.sub(r'[^a-z0-9]+', '-', text)
    return text.strip('-')

def make_progress_bar(solved, total, length=20):
    if total == 0:
        pct = 0
    else:
        pct = (solved / total)
    filled = int(round(pct * length))
    filled = min(length, max(0, filled))
    bar = "█" * filled + "░" * (length - filled)
    return f"`[{bar}]` {pct * 100:.1f}%"

def detect_language(content, default="Code"):
    if not content:
        return default
    if "class Solution" in content and ("public " in content or "int " in content):
        if "vector<" in content or "cout" in content or "#include" in content:
            return "C++"
        return "Java"
    if "def " in content and ":" in content:
        return "Python"
    if "#include" in content:
        return "C++"
    return default

def extract_slug_from_url(url):
    if not url:
        return None
    m = re.search(r'leetcode\.com/problems/([^/]+)', url)
    if m:
        return m.group(1).strip()
    m2 = re.search(r'geeksforgeeks\.org/problems/([^/]+)', url)
    if m2:
        return re.sub(r'\d+$', '', m2.group(1)).strip('-')
    return None

def parse_metadata_file(meta_path):
    info = {
        "title": None,
        "url": None,
        "difficulty": None,
        "platform": None,
        "language": None
    }
    if not meta_path.exists():
        return info
    try:
        data = json.loads(meta_path.read_text(encoding="utf-8", errors="ignore"))
        title = data.get("problemTitle") or data.get("title")
        url = data.get("problemUrl") or data.get("url")
        
        # If title is numeric like "0", extract from problemUrl
        if not title or title.isdigit():
            slug_from_url = extract_slug_from_url(url)
            if slug_from_url:
                title = slug_from_url.replace('-', ' ').title()

        info["title"] = title
        info["url"] = url
        diff = data.get("difficulty")
        if diff and diff.lower() != "unknown":
            info["difficulty"] = diff.capitalize()
        info["platform"] = data.get("platform")
        lang = data.get("language")
        if lang and len(lang) < 20:
            info["language"] = lang
        elif lang:
            info["language"] = detect_language(lang)
    except Exception:
        pass
    return info

def parse_readme_metadata(readme_path):
    info = {
        "title": None,
        "url": None,
        "difficulty": None
    }
    if not readme_path.exists():
        return info

    try:
        content = readme_path.read_text(encoding="utf-8", errors="ignore")
        match_link = re.search(r'<h2><a\s+href="([^"]+)">([^<]+)</a></h2>', content, re.IGNORECASE)
        if match_link:
            info["url"] = match_link.group(1).strip()
            info["title"] = match_link.group(2).strip()
        else:
            match_h = re.search(r'^#+\s+(?:\[([^\]]+)\]\(([^)]+)\)|([^\n]+))', content, re.MULTILINE)
            if match_h:
                if match_h.group(1):
                    info["title"] = match_h.group(1).strip()
                    info["url"] = match_h.group(2).strip()
                elif match_h.group(3):
                    info["title"] = match_h.group(3).strip()

        diff_match = re.search(r'Difficulty-(Easy|Medium|Hard|Basic|School)', content, re.IGNORECASE)
        if diff_match:
            info["difficulty"] = diff_match.group(1).capitalize()
        else:
            diff_text = re.search(r'\b(Easy|Medium|Hard|Basic|School)\b', content[:300], re.IGNORECASE)
            if diff_text:
                info["difficulty"] = diff_text.group(1).capitalize()
    except Exception:
        pass
    return info

def scan_solutions():
    solved_problems = []

    # 1. Scan LeetCode folder
    if LEETCODE_DIR.exists():
        for item in sorted(LEETCODE_DIR.iterdir()):
            if not item.is_dir() or item.name.startswith("."):
                continue

            folder_name = item.name
            num_match = re.match(r'^(\d+)[-_](.+)$', folder_name)
            problem_number = num_match.group(1) if num_match else None
            folder_slug = num_match.group(2) if num_match else folder_name

            readme_path = item / "README.md"
            meta = parse_readme_metadata(readme_path)
            title = meta["title"] or folder_slug.replace('-', ' ').title()
            url = meta["url"] or f"https://leetcode.com/problems/{folder_slug}/"
            difficulty = meta["difficulty"] or "Medium"

            code_file = None
            code_lang = None
            notes_file = None
            for f in sorted(item.iterdir()):
                if f.name == "Notes.md":
                    notes_file = f.relative_to(BASE_DIR).as_posix()
                elif f.suffix in CODE_EXTENSIONS and f.suffix != ".txt" and not code_file:
                    code_file = f.relative_to(BASE_DIR).as_posix()
                    code_lang = CODE_EXTENSIONS[f.suffix]

            solved_problems.append({
                "platform": "LeetCode",
                "folder_path": item.relative_to(BASE_DIR).as_posix(),
                "folder_name": folder_name,
                "problem_number": problem_number,
                "title": title,
                "slug": folder_slug,
                "url": url,
                "difficulty": difficulty,
                "solution_file": code_file,
                "language": code_lang or "Code",
                "notes_file": notes_file
            })

    # 2. Scan GFG folder
    if GFG_DIR.exists():
        # A. Standalone files in GFG/
        for f in sorted(GFG_DIR.iterdir()):
            if f.is_file() and not f.name.startswith("."):
                if f.name == ".gitkeep":
                    continue
                name_clean = re.sub(r'^\d+[-_]', '', f.stem)
                title = name_clean.replace('_', ' ').replace('-', ' ').title()
                content = f.read_text(encoding="utf-8", errors="ignore")
                lang = CODE_EXTENSIONS.get(f.suffix, "Code")
                if lang == "Code":
                    lang = detect_language(content)

                solved_problems.append({
                    "platform": "GeeksforGeeks",
                    "folder_path": f.relative_to(BASE_DIR).as_posix(),
                    "folder_name": f.name,
                    "problem_number": None,
                    "title": title,
                    "slug": make_slug(title),
                    "url": f"https://www.geeksforgeeks.org/problems/{make_slug(title)}/1",
                    "difficulty": "Medium",
                    "solution_file": f.relative_to(BASE_DIR).as_posix(),
                    "language": lang,
                    "notes_file": None
                })

        # B. Subdirectories in GFG/
        for root, dirs, files in os.walk(GFG_DIR):
            root_path = Path(root)
            if root_path == GFG_DIR or root_path.name.startswith("."):
                continue

            code_files = [f for f in files if Path(f).suffix in CODE_EXTENSIONS]
            has_readme = "README.md" in files
            has_meta = "metadata.json" in files

            if code_files or has_readme or has_meta:
                folder_name = root_path.name
                meta_json = parse_metadata_file(root_path / "metadata.json")
                meta_readme = parse_readme_metadata(root_path / "README.md")

                title = meta_json["title"] or meta_readme["title"]
                if not title or title.isdigit():
                    title = re.sub(r'^\d+[-_]', '', folder_name).replace('_', ' ').replace('-', ' ').title()

                url = meta_json["url"] or meta_readme["url"] or f"https://www.geeksforgeeks.org/problems/{make_slug(title)}/1"
                difficulty = meta_json["difficulty"] or meta_readme["difficulty"] or "Medium"
                platform = meta_json["platform"] or "GeeksforGeeks"

                code_file = None
                code_lang = meta_json.get("language")
                notes_file = None
                
                sorted_files = sorted(root_path.iterdir(), key=lambda p: (p.suffix == ".txt", p.name))
                for cf in sorted_files:
                    if cf.name == "Notes.md":
                        notes_file = cf.relative_to(BASE_DIR).as_posix()
                    elif cf.suffix in CODE_EXTENSIONS and not code_file:
                        code_file = cf.relative_to(BASE_DIR).as_posix()
                        if not code_lang or code_lang == "Code":
                            if cf.suffix != ".txt":
                                code_lang = CODE_EXTENSIONS[cf.suffix]
                            else:
                                txt_content = cf.read_text(encoding="utf-8", errors="ignore")
                                code_lang = detect_language(txt_content)

                solved_problems.append({
                    "platform": platform,
                    "folder_path": root_path.relative_to(BASE_DIR).as_posix(),
                    "folder_name": folder_name,
                    "problem_number": None,
                    "title": title,
                    "slug": make_slug(title),
                    "url": url,
                    "difficulty": difficulty,
                    "solution_file": code_file,
                    "language": code_lang or "Code",
                    "notes_file": notes_file
                })

    return solved_problems

def match_problems(sheet_data, solved_problems):
    sheet_index = []
    for cat, qs in sheet_data.items():
        for q in qs:
            sheet_index.append((cat, q))

    # Map qid -> list of solved submissions
    matched_sheet_solutions = {}
    extra_solved = []
    seen_extra_slugs = set()

    for solved in solved_problems:
        matched = False
        solved_norm = normalize(solved["title"])
        solved_slug = solved["slug"]
        solved_num = solved.get("problem_number")

        best_match = None
        best_ratio = 0.0

        for cat, q in sheet_index:
            q_norm = normalize(q["title"])
            q_slug = q["slug"]
            q_aliases = [normalize(a) for a in q.get("aliases", [])]
            q_slug_aliases = [a for a in q.get("aliases", [])]

            # 1. Exact match on normalized title or slug
            if solved_norm == q_norm or solved_slug == q_slug:
                best_match = q
                best_ratio = 1.0
                break

            # 2. Number or alias match
            if solved_norm in q_aliases or solved_slug in q_slug_aliases:
                best_match = q
                best_ratio = 1.0
                break

            if solved_num and solved_num in q_aliases:
                best_match = q
                best_ratio = 1.0
                break

            # 3. Fuzzy match
            ratio1 = difflib.SequenceMatcher(None, solved_norm, q_norm).ratio()
            ratio2 = difflib.SequenceMatcher(None, solved_slug, q_slug).ratio()
            ratio = max(ratio1, ratio2)

            if (solved_norm in q_norm or q_norm in solved_norm) and len(solved_norm) > 6 and len(q_norm) > 6:
                ratio = max(ratio, 0.90)

            if ratio > best_ratio:
                best_ratio = ratio
                best_match = q

        if best_match and best_ratio >= 0.82:
            qid = best_match["id"]
            if qid not in matched_sheet_solutions:
                matched_sheet_solutions[qid] = []
            matched_sheet_solutions[qid].append(solved)
            matched = True

        if not matched:
            if solved_slug not in seen_extra_slugs and solved_norm:
                seen_extra_slugs.add(solved_slug)
                extra_solved.append(solved)

    return matched_sheet_solutions, extra_solved

def generate_markdown(sheet_data, matched_sheet_solutions, extra_solved):
    total_sheet_qs = sum(len(qs) for qs in sheet_data.values())
    total_sheet_solved = len(matched_sheet_solutions)
    total_extra_solved = len(extra_solved)
    total_all_solved = total_sheet_solved + total_extra_solved
    overall_pct = (total_sheet_solved / total_sheet_qs * 100) if total_sheet_qs else 0

    lines = []
    lines.append("# 🚀 Apna College DSA Tracker & Practice Log\n")
    lines.append("> Automated progress tracking sheet for **Apna College (Shradha Didi & Aman Bhaiya) 375+ DSA Sheet** and extra DSA problem solutions synced from **LeetCode** and **GeeksforGeeks**.\n")

    badge_color = "brightgreen" if overall_pct > 50 else ("yellow" if overall_pct > 20 else "blue")
    lines.append("<p align=\"center\">")
    lines.append(f"  <img src=\"https://img.shields.io/badge/Apna_College_Sheet-{total_sheet_solved}%20%2F%20{total_sheet_qs}%20({overall_pct:.1f}%25)-{badge_color}?style=for-the-badge&logo=target\" alt=\"Sheet Progress\" />")
    lines.append(f"  <img src=\"https://img.shields.io/badge/Extra_Questions-{total_extra_solved}%20Solved-9cf?style=for-the-badge&logo=star\" alt=\"Extra Solved\" />")
    lines.append(f"  <img src=\"https://img.shields.io/badge/Total_Solved-{total_all_solved}%20Problems-orange?style=for-the-badge&logo=codeforces\" alt=\"Total Solved\" />")
    lines.append("</p>\n")

    lines.append("## 📊 Overall Progress\n")
    lines.append(f"**Apna College Sheet Progress:** {make_progress_bar(total_sheet_solved, total_sheet_qs, length=30)}\n")
    lines.append(f"- **Sheet Questions Solved:** `{total_sheet_solved} / {total_sheet_qs}` ({overall_pct:.1f}%)")
    lines.append(f"- **Extra Questions Solved:** `{total_extra_solved}`")
    lines.append(f"- **Total Combined DSA Problems Solved:** `{total_all_solved}`")
    lines.append(f"- **Last Updated:** `{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}`\n")

    lines.append("## 📑 Category-wise Breakdown\n")
    lines.append("| Category | Progress | Solved / Total | Percentage | Status |")
    lines.append("| :--- | :--- | :---: | :---: | :---: |")

    cat_stats = {}
    for cat, qs in sheet_data.items():
        c_solved = sum(1 for q in qs if q["id"] in matched_sheet_solutions)
        c_total = len(qs)
        c_pct = (c_solved / c_total * 100) if c_total else 0
        status_icon = "🎉 Done" if c_solved == c_total else ("⚡ In Progress" if c_solved > 0 else "⏳ Not Started")
        cat_stats[cat] = (c_solved, c_total, c_pct)
        lines.append(f"| [{cat}](#{make_slug(cat)}) | {make_progress_bar(c_solved, c_total, length=12)} | `{c_solved} / {c_total}` | `{c_pct:.1f}%` | {status_icon} |")

    lines.append(f"| **Overall Total** | {make_progress_bar(total_sheet_solved, total_sheet_qs, length=12)} | **`{total_sheet_solved} / {total_sheet_qs}`** | **`{overall_pct:.1f}%`** | **{total_sheet_solved} / {total_sheet_qs}** |\n")

    lines.append("## ⭐ Extra Questions Solved (Outside Apna College Sheet)\n")
    lines.append(f"> Here are `{total_extra_solved}` additional questions solved on LeetCode / GFG that enrich your DSA practice.\n")
    if extra_solved:
        lines.append("| # | Problem Name | Platform | Difficulty | Language | Solution File | Notes |")
        lines.append("| :---: | :--- | :---: | :---: | :---: | :---: | :---: |")
        for idx, item in enumerate(extra_solved, 1):
            prob_title = f"[{item['title']}]({item['url']})" if item['url'] else item['title']
            diff_badge = f"`{item['difficulty']}`" if item['difficulty'] else "`Medium`"
            sol_link = f"[{Path(item['solution_file']).name}]({item['solution_file']})" if item['solution_file'] else "-"
            notes_link = f"[Notes]({item['notes_file']})" if item.get("notes_file") else "-"
            lines.append(f"| {idx} | {prob_title} | `{item['platform']}` | {diff_badge} | {item['language']} | {sol_link} | {notes_link} |")
    else:
        lines.append("*No extra questions solved yet. Keep going!*\n")
    lines.append("")

    lines.append("## 📚 Apna College Sheet Details\n")
    for cat, qs in sheet_data.items():
        c_solved, c_total, c_pct = cat_stats[cat]
        open_attr = " open" if c_solved > 0 else ""
        lines.append(f"<details{open_attr}>")
        lines.append(f"<summary><h3 style=\"display:inline\" id=\"{make_slug(cat)}\">📂 {cat} &nbsp;—&nbsp; {c_solved}/{c_total} Solved ({c_pct:.1f}%)</h3></summary>\n")
        lines.append("| # | Status | Problem Title | Platform | Solution | Companies / Remarks |")
        lines.append("| :---: | :---: | :--- | :---: | :---: | :--- |")

        for idx, q in enumerate(qs, 1):
            is_solved = q["id"] in matched_sheet_solutions
            status = "✅ **Solved**" if is_solved else "⬜ *Unsolved*"
            
            if is_solved:
                sols = matched_sheet_solutions[q["id"]]
                primary_sol = sols[0]
                prob_url = primary_sol["url"] if primary_sol.get("url") else q.get("problem_url", "#")
                title_cell = f"[{q['title']}]({prob_url})"
                
                platforms = sorted(list(set(s['platform'] for s in sols)))
                plat_cell = " / ".join(f"`{p}`" for p in platforms)

                sol_links = []
                for s in sols:
                    if s.get("solution_file"):
                        sol_links.append(f"[{Path(s['solution_file']).name}]({s['solution_file']})")
                sol_cell = "<br>".join(sol_links) if sol_links else "-"
            else:
                prob_url = q.get("problem_url", "#")
                title_cell = f"[{q['title']}]({prob_url})"
                plat_cell = "-"
                sol_cell = "-"

            extra_info = []
            if q.get("companies"):
                extra_info.append(f"**Companies:** {q['companies']}")
            if q.get("remarks"):
                extra_info.append(f"💡 *{q['remarks']}*")
            info_str = "<br>".join(extra_info) if extra_info else "-"

            lines.append(f"| {idx} | {status} | {title_cell} | {plat_cell} | {sol_cell} | {info_str} |")

        lines.append("\n</details>\n")
    return "\n".join(lines)

def main():
    if not DATA_FILE.exists():
        print(f"Error: {DATA_FILE} not found. Run scripts/build_dataset.py first.")
        return

    with open(DATA_FILE, "r", encoding="utf-8") as f:
        sheet_data = json.load(f)

    solved_problems = scan_solutions()
    matched_solutions, extra_solved = match_problems(sheet_data, solved_problems)

    total_sheet = sum(len(qs) for qs in sheet_data.values())
    print(f"Scanned {len(solved_problems)} solved problems in repo.")
    print(f"Matched {len(matched_solutions)} / {total_sheet} Apna College sheet problems.")
    print(f"Found {len(extra_solved)} extra solved problems outside sheet.")

    content = generate_markdown(sheet_data, matched_solutions, extra_solved)
    with open(README_FILE, "w", encoding="utf-8") as f:
        f.write(content)

    print(f"Successfully updated {README_FILE}")

if __name__ == "__main__":
    main()
