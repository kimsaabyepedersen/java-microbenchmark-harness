package dk.anno1980;

class LinearSearch {

  static int find(int[] array, int toFind) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == toFind) return i;
    }
    return -1;
  }

}
