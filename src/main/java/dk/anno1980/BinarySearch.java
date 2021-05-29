package dk.anno1980;

class BinarySearch {

  static int find(int[] array, int toFind) {
    return find(array, toFind, 0, array.length - 1);
  }

  private static int find(int[] array, int toFind, int leftIndex, int rightIndex) {
    if (leftIndex > rightIndex) return -1;

    int pivot = (leftIndex + rightIndex) / 2;
    int valueAtPivot = array[pivot];

    if (valueAtPivot == toFind) return pivot;
    if (toFind < valueAtPivot) {
      return find(array, toFind, leftIndex, pivot - 1);
    }
    return find(array, toFind, pivot + 1, rightIndex);
  }

}
