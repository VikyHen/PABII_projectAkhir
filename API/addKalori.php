<?php
require("koneksi.php");

if($_SERVER["REQUEST_METHOD"] =="POST"){
    $nama = $_POST["nama"];
    $gambar = $_POST["gambar"];
    $kalori = $_POST["kalori"];
    
    $checkQuery = "SELECT * FROM tbl_kalori WHERE nama='$nama' AND gambar='$gambar' AND kalori='$kalori'";
    $checkResult = mysqli_query($connect, $checkQuery);
    
    if (mysqli_num_rows($checkResult) > 0) {
        $response["kode"] = 0;
        $response["pesan"] = "$nama". " Sudah Anda Tambahkan";
        echo json_encode($response);
        mysqli_close($connect);
        exit;
    }
    
    $perintah ="INSERT INTO tbl_kalori(nama, gambar, kalori) VALUES('$nama', '$gambar', '$kalori')";
    $eksekusi = mysqli_query($connect, $perintah);
    $cek = mysqli_affected_rows($connect);    
    
    if($cek > 0){
        $response["kode"] = 1;
    $response["pesan"] = "$nama". " Berhasil Ditambahkan";
    }
    else{
        $response["kode"] = 0;
    $response["pesan"] = "Ada Kesalahan. Gagal Menyimpan Data";
    }
}
else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connect);

?>