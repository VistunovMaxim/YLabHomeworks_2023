public class SequencesImpl implements Sequences {

    @Override
    public void a(int n) {
        System.out.print("A. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? i * 2 + "\n" : i * 2 + ", ");
        }
    }

    @Override
    public void b(int n) {
        System.out.print("B. ");
        for (int i = 1; i < n * 2; i += 2) {
            System.out.print(i == n * 2 - 1 ? i + "\n" : i + ", ");
        }
    }

    @Override
    public void c(int n) {
        System.out.print("С. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? (int) Math.pow(i, 2) + "\n" : (int) Math.pow(i, 2) + ", ");
        }
    }

    @Override
    public void d(int n) {
        System.out.print("D. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? (int) Math.pow(i, 3) + "\n" : (int) Math.pow(i, 3) + ", ");
        }
    }

    @Override
    public void e(int n) {
        System.out.print("E. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? (i % 2 > 0 ? 1 : -1) + "\n" : (i % 2 > 0 ? 1 : -1) + ", ");
        }
    }

    @Override
    public void f(int n) {
        System.out.print("F. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? (i % 2 > 0 ? i : -i) + "\n" : (i % 2 > 0 ? i : -i) + ", ");
        }
    }

    @Override
    public void g(int n) {
        System.out.print("G. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? (i % 2 > 0 ? (int) Math.pow(i, 2) : -(int) Math.pow(i, 2)) + "\n" :
                    (i % 2 > 0 ? (int) Math.pow(i, 2) : -(int) Math.pow(i, 2)) + ", ");
        }
    }

    @Override
    public void h(int n) {
        int lastNum = n % 2 == 0 ? n / 2 : n / 2 + 1;
        System.out.print("H. ");
        for (int i = 1; i <= lastNum; i++) {
            System.out.print(i == lastNum ? (n % 2 == 0 ? i + ", 0" : i) + "\n" : i + ", 0, ");
        }
    }

    @Override
    public void i(int n) {
        int previousNum = 1;
        System.out.print("I. ");
        for (int i = 1; i <= n; i++) {
            System.out.print(i == n ? previousNum * i + "\n" : previousNum * i + ", ");
            previousNum *= i;
        }
    }

    @Override
    public void j(int n) {
        int[] arr = new int[n];
        System.out.print("J. ");
        if (n == 0) return;
        if (n == 1) {
            System.out.println(1);
        } else if (n == 2) {
            System.out.println(1 + ", " + 1);
        } else {
            arr[0] = 1;
            arr[1] = 1;
            System.out.print(1 + ", " + 1 + ", ");
            for (int i = 2; i < n; i++) {
                arr[i] = arr[i - 2] + arr[i - 1];
                System.out.print(i == n - 1 ? arr[i] + "\n" : arr[i] + ", ");

            }
        }
    }
}
