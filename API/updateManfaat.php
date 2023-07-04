<?php
require("koneksi.php");

if($_SERVER["REQUEST_METHOD"] =="POST"){
    $id_manfaat = $_POST["id_manfaat"];
    $kandungan = $_POST["kandungan"];
    $manfaatKandungan = $_POST["manfaatKandungan"];
    $jumlah = $_POST["jumlah"];
    
    $perintah ="UPDATE tbl_manfaat SET kandungan='$kandungan', manfaatKandungan='$manfaatKandungan', jumlah='$jumlah' where id_manfaat='$id_manfaat'";
    $eksekusi = mysqli_query($connect, $perintah);
    $cek = mysqli_affected_rows($connect);
    
    if($cek > 0){
        $response["kode"] = 1;
    $response["pesan"] = "Sukses Mengubah data";
    }
    else{
        $response["kode"] = 0;
    $response["pesan"] = "Ada Kesalahan. Gagal Mengubah Data";
    }
}
else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Update Data";
}

echo json_encode($response);
mysqli_close($connect);