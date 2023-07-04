<?php
    require("koneksi.php");
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        $id = $_POST["id"];
        
        // Mengambil nama gambar sebelum menghapus data
        $perintah = "SELECT gambar FROM tbl_food WHERE id='$id'";
        $eksekusi = mysqli_query($connect, $perintah);
        if ($row = mysqli_fetch_assoc($eksekusi)) {
            $gambar = $row["gambar"];
            $folderPath = "images/";
            $gambarPath = $folderPath . $gambar;

            // Menghapus gambar dari server
            if (file_exists($gambarPath)) {
                unlink($gambarPath);
            }

            // Menghapus data dari tabel
            $perintah = "DELETE FROM tbl_food WHERE id='$id'";
            $eksekusi = mysqli_query($connect, $perintah);
            
            if (mysqli_affected_rows($connect) > 0) {
                $response["kode"] = 1;
                $response["pesan"] = "Sukses Menghapus Data";
            } else {
                $response["kode"] = 0;
                $response["pesan"] = "Ada Kesalahan. Gagal Menghapus Data";
            }
        } else {
            $response["kode"] = 0;
            $response["pesan"] = "Data Tidak Ditemukan";
        }
    }
    else{
        $response["kode"] = 0;
        $response["pesan"] = "Tidak Ada Data yang Dihapus";
    }
    
    echo json_encode($response);
    mysqli_close($connect);
?>
