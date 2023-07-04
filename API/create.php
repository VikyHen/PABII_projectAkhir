<?php
require("koneksi.php");

if($_SERVER["REQUEST_METHOD"] =="POST"){
    $nama = $_POST["nama"];
    $gambar = $_POST["gambar"];
    $deskripsi = $_POST["deskripsi"];
    $kalori = $_POST["kalori"];
    
    $gambarData = base64_decode($gambar);
    $folderPath = "images/";
    $gambarName = "image". time(). ".jpg";
    $gambarPath = $folderPath . $gambarName;
    if(file_put_contents($gambarPath, $gambarData)){
        $perintah ="INSERT INTO tbl_food(nama, gambar, deskripsi, kalori) VALUES('$nama', '$gambarName', '$deskripsi', '$kalori')";
        $eksekusi = mysqli_query($connect, $perintah);
    }
    
    $cek = mysqli_affected_rows($connect);    
    //$filename= "image_" . time() . ".jpg";
    //$filepath = "/images/" . $filename;
    //file_put_contents($filepath, $datagambar);
    
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