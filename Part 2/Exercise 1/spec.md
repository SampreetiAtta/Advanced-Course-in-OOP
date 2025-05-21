### Given code:

```java
class EmptyArray extends Exception {}

float avg(int[] nums) throws EmptyArray { // 1
    int sum = 0;
    if (nums == null || nums.length == 0)
        throw new EmptyArray(); // 2
    for(int n: nums) sum += n;
    return sum / nums.length;
}
void main() {
    int[] nums = new int[] { 1, 2 };
    Float result;
    try { result = avg(nums); } // 3
    catch(EmptyArray e) {} // 3
}
```

### What happens?

* For [1, 2]: avg() returns (1 + 2) / 2 = 1.5, so result = 1.5.
* For []: avg() throws EmptyArray, so result is never assigned inside the try block. The variable remains uninitialized and will cause a compile-time error if accessed after the try-catch without initialization.


