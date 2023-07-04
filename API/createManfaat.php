<?php
require("koneksi.php");

if($_SERVER["REQUEST_METHOD"] =="POST"){
    $kandungan = $_POST["kandungan"];
    $manfaatKandungan = $_POST["manfaatKandungan"];
    $jumlah = $_POST["jumlah"];
    $food_id = $_POST["food_id"];

    $perintah ="INSERT INTO tbl_manfaat(food_id, kandungan, manfaatKandungan, jumlah) VALUES('$food_id', '$kandungan', '$manfaatKandungan', '$jumlah')";
    $eksekusi = mysqli_query($connect, $perintah);
    $cek = mysqli_affected_rows($connect);
    
    if($cek > 0){
        $response["kode"] = 1;
    $response["pesan"] = "Sukses Menyimpan data";
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