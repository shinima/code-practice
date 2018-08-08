package algorithms;

public class QuickSort {
  public static void quickSort(int[] nums) {
    quickSort(nums, 0, nums.length - 1);
  }

  // start和end是inclusive的
  static void quickSort(int[] nums, int start, int end) {
    int count = end - start + 1;
    if (count <= 5) {
      insertSort(nums, start, end);
      return;
    }
    int mid = (start + end) / 2;
    if (nums[start] > nums[mid]) swap(nums, start, mid);
    if (nums[start] > nums[end]) swap(nums, start, end);
    if (nums[mid] > nums[end]) swap(nums, mid, end);
    // 到这里我们可以保证 nums[start] <= nums[mid] <= nums[end]

    // 选择nums[mid]作为pivot并将其放在下标为 end - 1 的位置
    int pivot = nums[mid];
    swap(nums, mid, end - 1);
    // 对 nums[start+1 ... end-2] 进行分组（把小的放左边，把大的放右边）
    // nums[start] 和 nums[end] 恰好可以作为"哨兵"（sentinel）
    int left = start + 1;
    int right = end - 2;
    while (true) {
      while (nums[left] < pivot) {
        left++;
      }
      while (nums[right] > pivot) {
        right--;
      }
      if (left < right) {
        swap(nums, left, right);
        left++;
        right--;
      } else {
        break;
      }
    }
    // 跳出循环时，left在右，right在左
    // nums[left] 恰好是大于等于 pivot 的第一个数字
    // pivot恰好可以放在left的位置
    swap(nums, left, end - 1);
    quickSort(nums, start, left - 1);
    quickSort(nums, left + 1, end);
  }

  private static void insertSort(int nums[], int start, int end) {
    for (int i = start; i <= end; i++) {
      int minIndex = i;
      for (int j = i + 1; j <= end; j++) {
        if (nums[j] < nums[minIndex]) {
          minIndex = j;
        }
      }
      swap(nums, i, minIndex);
    }
  }

  private static void swap(int nums[], int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}
