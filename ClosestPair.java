import java.util.*;

class ClosestPair {

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Eucklid distance
    static double dist(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Brutforce for small values (≤3)
    static double bruteForce(Point[] points, int left, int right) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                min = Math.min(min, dist(points[i], points[j]));
            }
        }
        return min;
    }

    // Checking way (stuff sorted already by y)
    static double stripClosest(List<Point> strip, double d) {
        double min = d;
        int n = strip.size();

        for (int i = 0; i < n; i++) {
            // checking only neibors by y (до 7–8 точек)
            for (int j = i + 1; j < n && (strip.get(j).y - strip.get(i).y) < min; j++) {
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }

    // Main recursive func
    static double closestUtil(Point[] pointsX, Point[] pointsY, int left, int right) {
        // If small values bruteforcing
        if (right - left <= 3) {
            return bruteForce(pointsX, left, right);
        }

        int mid = (left + right) / 2;
        Point midPoint = pointsX[mid];

        // делим pointsY на левую и правую половины
        List<Point> leftY = new ArrayList<>();
        List<Point> rightY = new ArrayList<>();
        for (Point p : pointsY) {
            if (p.x <= midPoint.x) {
                leftY.add(p);
            } else {
                rightY.add(p);
            }
        }

        // рекурсивные вызовы
        double dl = closestUtil(pointsX, leftY.toArray(new Point[0]), left, mid);
        double dr = closestUtil(pointsX, rightY.toArray(new Point[0]), mid + 1, right);

        double d = Math.min(dl, dr);

        // формируем "полосу" вокруг середины
        List<Point> strip = new ArrayList<>();
        for (Point p : pointsY) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip.add(p);
            }
        }

        return Math.min(d, stripClosest(strip, d));
    }

    // Основной интерфейс
    static double closest(Point[] points) {
        // сортировка по x
        Point[] pointsX = points.clone();
        Arrays.sort(pointsX, Comparator.comparingDouble(p -> p.x));

        // сортировка по y
        Point[] pointsY = points.clone();
        Arrays.sort(pointsY, Comparator.comparingDouble(p -> p.y));

        return closestUtil(pointsX, pointsY, 0, points.length - 1);
    }

    // Тест
    public static void main(String[] args) {
        Point[] points = {
                new Point(2, 3), new Point(12, 30), new Point(40, 50),
                new Point(5, 1), new Point(12, 10), new Point(3, 4)
        };

        System.out.printf("The smallest distance is %.4f\n", closest(points));
    }
}
