import java.util.*;

class Solution {

    class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            prod = 1;
            cnt = new int[k];
        }
    }

    int k;
    Node[] tree;

    // Merge two segments: left + right
    Node merge(Node left, Node right) {

        Node res = new Node(k);

        // Product of entire segment
        res.prod = (left.prod * right.prod) % k;

        // Prefixes completely inside left
        for (int r = 0; r < k; r++) {
            res.cnt[r] += left.cnt[r];
        }

        // Prefixes that start in left and continue into right
        for (int r = 0; r < k; r++) {

            int newRemainder = (left.prod * r) % k;

            res.cnt[newRemainder] += right.cnt[r];
        }

        return res;
    }

    // Build tree
    void build(int node, int l, int r, int[] nums) {

        if (l == r) {

            int value = nums[l] % k;

            tree[node] = new Node(k);

            // Product of this one-element segment
            tree[node].prod = value;

            // Only one non-empty prefix: itself
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(2 * node + 1, l, mid, nums);
        build(2 * node + 2, mid + 1, r, nums);

        tree[node] = merge(
            tree[2 * node + 1],
            tree[2 * node + 2]
        );
    }

    // Point update
    void update(int node, int l, int r, int index, int value) {

        if (l == r) {

            value %= k;

            tree[node] = new Node(k);

            tree[node].prod = value;
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(2 * node + 1, l, mid, index, value);
        } else {
            update(2 * node + 2, mid + 1, r, index, value);
        }

        tree[node] = merge(
            tree[2 * node + 1],
            tree[2 * node + 2]
        );
    }

    // Range query
    Node query(int node, int l, int r, int ql, int qr) {

        // Completely inside
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        // Completely in left
        if (qr <= mid) {
            return query(
                2 * node + 1,
                l,
                mid,
                ql,
                qr
            );
        }

        // Completely in right
        if (ql > mid) {
            return query(
                2 * node + 2,
                mid + 1,
                r,
                ql,
                qr
            );
        }

        // Crosses both sides
        Node left = query(
            2 * node + 1,
            l,
            mid,
            ql,
            qr
        );

        Node right = query(
            2 * node + 2,
            mid + 1,
            r,
            ql,
            qr
        );

        return merge(left, right);
    }

    public int[] resultArray(
        int[] nums,
        int k,
        int[][] queries
    ) {

        this.k = k;

        int n = nums.length;

        tree = new Node[4 * n];

        build(0, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // 1. Update nums[index]
            update(
                0,
                0,
                n - 1,
                index,
                value
            );

            // 2. We keep nums[start ... n-1]
            Node result = query(
                0,
                0,
                n - 1,
                start,
                n - 1
            );

            // 3. Number of prefixes having
            // product % k == x
            ans[i] = result.cnt[x];
        }

        return ans;
    }
}