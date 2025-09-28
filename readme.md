# Algorithm Report

## This repository contains implementations and benchmarks for several classical divide-and-conquer algorithms: QuickSort, MergeSort, Deterministic Select (Median-of-Medians), and Closest Pair of Points.

📌 Architecture Notes
Architecture Overview (depth and allocations control)

Metrics collection: Each algorithm run is wrapped with Metrics.startTimer/stopTimer. Comparisons are tracked via addComparisons, and recursion depth is monitored through paired calls to enterRecursion and exitRecursion, which also record the maximum observed depth.

Recursion depth: Recursive algorithms increase depth counters on entry and restore on exit. QuickSort is designed to recurse only into the smaller partition, handling the larger one iteratively. This ensures recursion depth remains close to O(log n), even with unbalanced partitions. MergeSort, DeterministicSelect, and ClosestPair divide problems recursively, and their depth also scales logarithmically with input size.

Memory usage: Only deliberate algorithmic allocations are logged through Metrics.addAllocation. MergeSort allocates a single auxiliary buffer of size n per run (allocations = n). QuickSort and DeterministicSelect are in-place (allocations = 0). ClosestPair creates temporary lists and performs sorting inside the strip phase, but those allocations aren’t counted, so CSV output still shows 0.

Complexity Analysis

MergeSort: Recurrence T(n) = 2T(n/2) + O(n). By the Master Theorem (a=2, b=2, f(n)=Θ(n)), runtime is O(n log n). Stack depth = O(log n). Memory overhead is O(n) due to the auxiliary buffer, which also helps with cache efficiency.

QuickSort (randomized, smaller-side recursion): Expected recurrence T(n) = T(U) + T(n–1–U) + O(n), where pivot position U is uniform. Intuitively (via Master Theorem/Akra–Bazzi), runtime averages O(n log n). 
Restricting recursion to the smaller side keeps depth O(log n). Worst case remains O(n²), though rare under random pivoting.
DeterministicSelect (median of medians): Recurrence T(n) ≤ T(n/5) + T(7n/10) + O(n). 
Akra–Bazzi theorem gives T(n) = O(n). Recursion depth = O(log n). In-place partitioning minimizes memory.
Closest Pair of Points: Initial sorting by x takes O(n log n). 
Divide-and-conquer recurrence T(n) = 2T(n/2) + O(n), so runtime is O(n log n). This version re-sorts the strip by y at each recursion level instead of maintaining a global y-order. 
That adds constant-factor work but overall complexity remains O(n log n).

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

# Points pre-sorted by x and y.
Efficient “strip” check using neighbor scanning (7–8 candidates only).
📊 Recurrence Analysis

## QuickSort

T(n) = T(k) + T(n−k−1) + O(n)

Average case: O(n log n)
Worst case: O(n²) (unlikely due to randomization)

## MergeSort

T(n) = 2T(n/2) + O(n)


→ Always O(n log n) with recursion depth = log₂ n.

## Deterministic Select

T(n) ≤ T(n/5) + T(7n/10) + O(n)


→ By Akra–Bazzi: O(n) guaranteed.

## Closest Pair

T(n) = 2T(n/2) + O(n)


→ Identical to MergeSort: O(n log n).

## 📈 Plots & Constant-Factor Effects
<img width="1009" height="586" alt="image" src="https://github.com/user-attachments/assets/1dbbb6c9-d990-4ac7-a00f-833e71814f61" />


<img width="1009" height="692" alt="image" src="https://github.com/user-attachments/assets/3ebd5805-ef1e-4ae4-9b09-90b2cf8d2be6" />


<img width="1009" height="601" alt="image" src="https://github.com/user-attachments/assets/6ee137f2-6918-422f-bed9-7d9b07cc8d7f" />

## Practical benchmarks (time vs input size n):

QuickSort & MergeSort → Extremely fast (milliseconds for 10⁴–10⁵ elements).
Deterministic Select → Linear growth but with higher constants (extra pivot logic).
Closest Pair → Asymptotically O(n log n), but slower in practice due to sorting & object overhead.

##Constant-factor observations:

MergeSort benefits from cache efficiency (sequential access).
QuickSort has low overhead, randomization helps.
Deterministic Select incurs extra work for pivoting.
Java GC introduces occasional noise but not critical.

## ✅ Summary
QuickSort & MergeSort → Theoretical O(n log n) and very fast in practice.
Deterministic Select → Linear O(n), but slower constants.
Closest Pair → Matches O(n log n) but suffers from overheads.





