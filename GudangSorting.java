import java.util.ArrayList;
import java.util.Scanner;

class Barang {
    String nama;
    int jumlah;

    Barang(String nama, int jumlah) {
        this.nama = nama;
        this.jumlah = jumlah;
    }

    @Override
    public String toString() {
        return "Nama Barang: " + nama + ", Jumlah: " + jumlah;
    }
}

public class GudangSorting {
    static ArrayList<Barang> daftarBarang = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Sistem Gudang ===");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Lihat Daftar Barang");
            System.out.println("3. Sorting Berdasarkan Nama (Bubble Sort)");
            System.out.println("4. Sorting Berdasarkan Jumlah Stok (Quick Sort)");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 
            switch (pilihan) {
                case 1 -> tambahBarang();
                case 2 -> tampilkanBarang();
                case 3 -> bubbleSortByNama();
                case 4 -> quickSortByJumlah();
                case 5 -> {
                    System.out.println("Keluar dari sistem. Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }

    public static void tambahBarang() {
        System.out.println("Masukkan barang dengan format 'NamaBarang:JumlahStok', pisahkan dengan koma untuk beberapa barang.");
        System.out.println("Contoh: Laptop:10, Keyboard:5, Mouse:15");
        System.out.print("Input: ");
        String input = scanner.nextLine();

        String[] barangArray = input.split(",");
        for (String barangData : barangArray) {
            String[] data = barangData.split(":");
            if (data.length == 2) {
                try {
                    String nama = data[0].trim();
                    int jumlah = Integer.parseInt(data[1].trim());
                    daftarBarang.add(new Barang(nama, jumlah));
                    System.out.println("Barang '" + nama + "' dengan stok " + jumlah + " berhasil ditambahkan!");
                } catch (NumberFormatException e) {
                    System.out.println("Format jumlah tidak valid untuk input: " + barangData);
                }
            } else {
                System.out.println("Format barang tidak valid: " + barangData);
            }
        }
    }

    public static void tampilkanBarang() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Tidak ada barang di dalam gudang.");
        } else {
            System.out.println("\nDaftar Barang:");
            for (Barang barang : daftarBarang) {
                System.out.println(barang);
            }
        }
    }

    public static void bubbleSortByNama() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Tidak ada barang untuk disortir.");
            return;
        }

        boolean ascending = getSortingOrder();

        int n = daftarBarang.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean condition = ascending
                        ? daftarBarang.get(j).nama.compareToIgnoreCase(daftarBarang.get(j + 1).nama) > 0
                        : daftarBarang.get(j).nama.compareToIgnoreCase(daftarBarang.get(j + 1).nama) < 0;

                if (condition) {
                    Barang temp = daftarBarang.get(j);
                    daftarBarang.set(j, daftarBarang.get(j + 1));
                    daftarBarang.set(j + 1, temp);
                }
            }
        }

        System.out.println("Daftar barang telah disortir berdasarkan nama:");
        tampilkanBarang();
    }

    public static void quickSortByJumlah() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Tidak ada barang untuk disortir.");
            return;
        }

        boolean ascending = getSortingOrder();
        quickSortByJumlah(0, daftarBarang.size() - 1, ascending);

        System.out.println("Daftar barang telah disortir berdasarkan jumlah stok:");
        tampilkanBarang();
    }

    public static boolean getSortingOrder() {
        while (true) {
            System.out.print("Urutkan secara (1) Ascending atau (2) Descending: ");
            int opsi = scanner.nextInt();
            scanner.nextLine(); 

            if (opsi == 1) {
                return true; // Ascending
            } else if (opsi == 2) {
                return false; // Descending
            } else {
                System.out.println("Pilihan tidak valid. Silakan pilih (1) atau (2).");
            }
        }
    }

    public static void quickSortByJumlah(int low, int high, boolean ascending) {
        if (low < high) {
            int pi = partition(low, high, ascending);
            quickSortByJumlah(low, pi - 1, ascending);
            quickSortByJumlah(pi + 1, high, ascending);
        }
    }

    public static int partition(int low, int high, boolean ascending) {
        int pivot = daftarBarang.get(high).jumlah;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            boolean condition = ascending
                    ? daftarBarang.get(j).jumlah < pivot
                    : daftarBarang.get(j).jumlah > pivot;

            if (condition) {
                i++;
                Barang temp = daftarBarang.get(i);
                daftarBarang.set(i, daftarBarang.get(j));
                daftarBarang.set(j, temp);
            }
        }

        Barang temp = daftarBarang.get(i + 1);
        daftarBarang.set(i + 1, daftarBarang.get(high));
        daftarBarang.set(high, temp);

        return i + 1;
    }
}
