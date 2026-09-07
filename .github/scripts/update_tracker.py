#!/usr/bin/env python3
import os
import json
import re
import urllib.parse

REPO_ROOT = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
SCRIPTS_DIR = os.path.join(REPO_ROOT, ".github", "scripts")
DATA_FILE = os.path.join(SCRIPTS_DIR, "apna_college_375.json")
README_FILE = os.path.join(REPO_ROOT, "README.md")

def normalize_text(text):
    if not text:
        return ""
    words = re.findall(r"[a-z0-9]+", text.lower())
    stopwords = {"the", "a", "an", "in", "of", "to", "for", "with", "and", "or", "on", "at", "from", "by", "given", "using", "problem"}
    filtered = [w for w in words if w not in stopwords]
    return "".join(filtered) if filtered else "".join(words)

def extract_slug(url):
    if not url:
        return ""
    clean = url.rstrip("/").split("?")[0]
    parts = clean.split("/")
    slug = parts[-2] if parts[-1] == "1" and len(parts) >= 2 else parts[-1]
    return re.sub(r"\d+$", "", slug).strip("-").lower()

def get_progress_bar(percent, width=25):
    filled = int(round(width * percent / 100))
    return "█" * filled + "░" * (width - filled)

def encode_path(p):
    return urllib.parse.quote(p, safe="/#")

def scan_solved_problems():
    solved = []
    
    # 1. LeetCode directory
    lc_dir = os.path.join(REPO_ROOT, "leetcode")
    if os.path.isdir(lc_dir):
        for entry in sorted(os.listdir(lc_dir)):
            full_path = os.path.join(lc_dir, entry)
            if not os.path.isdir(full_path) or entry.startswith("."):
                continue
            
            # Format: <id>-<slug> e.g. 53-maximum-subarray
            m = re.match(r"^(\d+)-(.*)$", entry)
            slug = m.group(2).lower() if m else entry.lower()
            title = ""
            readme_path = os.path.join(full_path, "README.md")
            if os.path.exists(readme_path):
                try:
                    with open(readme_path, "r", encoding="utf-8", errors="ignore") as f:
                        first_line = f.readline()
                        tm = re.search(r">([^<]+)</a>", first_line)
                        if tm:
                            title = tm.group(1).strip()
                except Exception:
                    pass
            
            rel_path = os.path.relpath(full_path, REPO_ROOT)
            solved.append({
                "source": "LeetCode",
                "folder": entry,
                "path": rel_path,
                "slug": slug,
                "title": title or slug.replace("-", " ").title(),
                "url": f"https://leetcode.com/problems/{slug}/"
            })
            
    # 2. Geeks For Geeks directory
    gfg_dir = os.path.join(REPO_ROOT, "Geeks For Geeks")
    if os.path.isdir(gfg_dir):
        for entry in sorted(os.listdir(gfg_dir)):
            full_path = os.path.join(gfg_dir, entry)
            if not os.path.isdir(full_path) or entry.startswith("."):
                continue
            
            title = entry
            url = ""
            meta_path = os.path.join(full_path, "metadata.json")
            if os.path.exists(meta_path):
                try:
                    with open(meta_path, "r", encoding="utf-8") as f:
                        mdata = json.load(f)
                        title = mdata.get("problemTitle", title)
                        url = mdata.get("problemUrl", "")
                except Exception:
                    pass
                    
            slug = extract_slug(url) if url else re.sub(r"[^a-zA-Z0-9]+", "-", entry.lower()).strip("-")
            rel_path = os.path.relpath(full_path, REPO_ROOT)
            solved.append({
                "source": "GFG",
                "folder": entry,
                "path": rel_path,
                "slug": slug,
                "title": title,
                "url": url
            })

    return solved

def main():
    if not os.path.exists(DATA_FILE):
        print(f"Error: {DATA_FILE} not found.")
        return

    with open(DATA_FILE, "r", encoding="utf-8") as f:
        questions = json.load(f)

    solved_items = scan_solved_problems()
    
    # Matching
    matched_questions = {}  # q_id -> list of solved_items
    used_solved_paths = set()

    for q in questions:
        q_id = q["id"]
        q_slug = extract_slug(q["url"])
        q_norm = normalize_text(q["name"])
        q_slug_norm = normalize_text(q_slug)
        
        target_slugs = set(q.get("aliases", []))
        if q_slug:
            target_slugs.add(q_slug)
            target_slugs.add(q_slug_norm)
            
        matches = []
        for s in solved_items:
            s_norm_title = normalize_text(s["title"])
            s_norm_slug = normalize_text(s["slug"])
            
            # Check slug / alias match
            if s["slug"] in target_slugs or s_norm_slug in target_slugs:
                matches.append(s)
                used_solved_paths.add(s["path"])
                continue
                
            # Check normalized title match
            if q_norm and (q_norm == s_norm_title or q_norm == s_norm_slug):
                matches.append(s)
                used_solved_paths.add(s["path"])
                continue
                
            # Check normalized slug with normalized title
            if q_slug_norm and (q_slug_norm == s_norm_title or q_slug_norm == s_norm_slug):
                matches.append(s)
                used_solved_paths.add(s["path"])
                continue

        if matches:
            matched_questions[q_id] = matches

    total_q = len(questions)
    total_solved = len(matched_questions)
    overall_percent = (total_solved / total_q * 100) if total_q else 0.0

    # Additional Solved Questions
    extra_solved = [s for s in solved_items if s["path"] not in used_solved_paths]
    total_all_solved = total_solved + len(extra_solved)

    # Group by Topic
    topics = {}
    for q in questions:
        t = q["topic"]
        if t not in topics:
            topics[t] = []
        topics[t].append(q)

    # Build README Markdown
    lines = []
    lines.append("# 🎯 DSA Tracker - Apna College Sheet (375 Questions)")
    lines.append("")
    lines.append("> Automated progress tracking for **DSA by Shradha Didi & Aman Bhaiya (Apna College)**.")
    lines.append("> Automatically synced via **LeetSync** (`leetcode/`), **GeekSync** (`Geeks For Geeks/`), and **GitHub Actions**.")
    lines.append("")
    lines.append("---")
    lines.append("")
    lines.append("## 📊 Overall Progress Summary")
    lines.append("")
    lines.append("| Metric | Solved | Total | Progress |")
    lines.append("| :--- | :---: | :---: | :--- |")
    lines.append(f"| **🎯 Apna College Sheet** | `{total_solved}` | `{total_q}` | `{get_progress_bar(overall_percent, 18)}` **{overall_percent:.1f}%** |")
    lines.append(f"| **🌟 Outside Sheet Problems** | `{len(extra_solved)}` | `-` | `{get_progress_bar(100, 18)}` **Tracked** |")
    lines.append(f"| **🔥 Total Solved in Repo** | `{total_all_solved}` | `-` | **All Platforms** |")
    lines.append("")
    lines.append("---")
    lines.append("")
    lines.append("## 📑 Topic Summary")
    lines.append("")
    lines.append("| Topic | Solved | Total | Progress | Link |")
    lines.append("| :--- | :---: | :---: | :--- | :---: |")

    for t_name, t_questions in topics.items():
        t_solved = sum(1 for q in t_questions if q["id"] in matched_questions)
        t_total = len(t_questions)
        t_percent = (t_solved / t_total * 100) if t_total else 0.0
        anchor = re.sub(r"[^a-z0-9]+", "-", t_name.lower()).strip("-")
        lines.append(f"| **{t_name}** | `{t_solved}` | `{t_total}` | `{get_progress_bar(t_percent, 15)}` {t_percent:.1f}% | [View](#{anchor}) |")

    lines.append("")
    lines.append("---")
    lines.append("")

    # Detailed tables for each topic
    for t_name, t_questions in topics.items():
        t_solved = sum(1 for q in t_questions if q["id"] in matched_questions)
        t_total = len(t_questions)
        t_percent = (t_solved / t_total * 100) if t_total else 0.0
        
        lines.append(f"### {t_name} ({t_solved}/{t_total})")
        lines.append("")
        lines.append("| Status | # | Problem | Companies | Notes / Remarks | Solution |")
        lines.append("| :---: | :---: | :--- | :--- | :--- | :--- |")

        for q in t_questions:
            q_id = q["id"]
            is_solved = q_id in matched_questions
            status = "✅" if is_solved else "⬜"
            
            # Problem link
            if q["url"]:
                problem_link = f"[{q['name']}]({q['url']})"
            else:
                problem_link = q["name"]
                
            companies = q["companies"].replace("|", "/") if q["companies"] else "-"
            remarks = q["remarks"].replace("|", "/") if q["remarks"] else "-"
            
            # Solution link(s)
            if is_solved:
                sol_links = []
                for s in matched_questions[q_id]:
                    enc_path = encode_path(s["path"])
                    label = "LeetCode" if s["source"] == "LeetCode" else "GFG"
                    sol_links.append(f"[{label}]({enc_path})")
                solution_str = " \\| ".join(sol_links)
            else:
                solution_str = "-"

            lines.append(f"| {status} | {q_id} | {problem_link} | {companies} | {remarks} | {solution_str} |")

        lines.append("")
        lines.append("[⬆ Back to Summary](#-topic-summary)")
        lines.append("")
        lines.append("---")
        lines.append("")

    with open(README_FILE, "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")

    print(f"Updated README.md: {total_solved}/{total_q} Apna College questions tracked. {len(extra_solved)} additional solved.")

if __name__ == "__main__":
    main()
