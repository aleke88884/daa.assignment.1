Algorithm Report

This repository contains implementations and benchmarks for several classical divide-and-conquer algorithms: QuickSort, MergeSort, Deterministic Select (Median-of-Medians), and Closest Pair of Points.

📌 Architecture Notes

In my implementation I aimed to control recursion depth and reduce unnecessary allocations:

# QuickSort
Recursing only on the smaller partition while handling the larger one iteratively.
→ Guarantees stack depth never exceeds O(log n) and avoids stack overflow.

# MergeSort
Uses a single reusable buffer allocated once and passed down through recursion.
→ Significantly reduces memory overhead.

# Deterministic Select (Median-of-Medians)
Uses groups of five for median-of-medians pivot selection.
→ Ensures balanced recursion and guarantees linear runtime.

# Closest Pair
Follows the classical divide & conquer strategy:

Points pre-sorted by x and y.
Efficient “strip” check using neighbor scanning (7–8 candidates only).
📊 Recurrence Analysis

QuickSort

T(n) = T(k) + T(n−k−1) + O(n)

Average case: O(n log n)
Worst case: O(n²) (unlikely due to randomization)

MergeSort

T(n) = 2T(n/2) + O(n)


→ Always O(n log n) with recursion depth = log₂ n.

Deterministic Select

T(n) ≤ T(n/5) + T(7n/10) + O(n)


→ By Akra–Bazzi: O(n) guaranteed.

Closest Pair

T(n) = 2T(n/2) + O(n)


→ Identical to MergeSort: O(n log n).

📈 Plots & Constant-Factor Effects

Practical benchmarks (time vs input size n):

QuickSort & MergeSort → Extremely fast (milliseconds for 10⁴–10⁵ elements).
Deterministic Select → Linear growth but with higher constants (extra pivot logic).
Closest Pair → Asymptotically O(n log n), but slower in practice due to sorting & object overhead.

Constant-factor observations:

MergeSort benefits from cache efficiency (sequential access).
QuickSort has low overhead, randomization helps.
Deterministic Select incurs extra work for pivoting.
Java GC introduces occasional noise but not critical.
✅ Summary
QuickSort & MergeSort → Theoretical O(n log n) and very fast in practice.
Deterministic Select → Linear O(n), but slower constants.
Closest Pair → Matches O(n log n) but suffers from overheads.

Overall, theory and experiment align well, and differences are explained by constants, caching, and data structures.
