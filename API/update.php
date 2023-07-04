<?php
    require("koneksi.php");
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        $id = $_POST["id"];
        $nama = $_POST["nama"];
        $gambar = $_POST["gambar"];
        $deskripsi = $_POST["deskripsi"];
        $kalori = $_POST["kalori"];
        
        $gambarData = base64_decode($gambar);
        $folderPath = "images/";
        $gambarName = "image". time(). ".jpg";
        $gambarPath = $folderPath . $gambarName;
        
        $perintah1 = "SELECT gambar FROM tbl_food WHERE id='$id'";
        $eksekusi1 = mysqli_query($connect, $perintah1);
        if ($row = mysqli_fetch_assoc($eksekusi1)) {
            $gambarLama = $row["gambar"];
            $pathGambarLama = $folderPath . $gambarLama;
            if (file_exists($pathGambarLama)) {
                unlink($pathGambarLama);
            }
        }
        
        if(file_put_contents($gambarPath, $gambarData)){
            $perintah ="UPDATE tbl_food SET nama='$nama', gambar='$gambarName', deskripsi='$deskripsi', kalori='$kalori' WHERE id='$id'";
            $eksekusi = mysqli_query($connect, $perintah);
        }
        
        $cek = mysqli_affected_rows($connect);
        
        if($cek>0){
            $response["kode"] = 1;
            $response["pesan"] = "Sukses Mengubah Data";
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
?>