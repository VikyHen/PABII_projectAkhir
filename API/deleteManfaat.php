<?php
    require("koneksi.php");
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        $id_manfaat = $_POST["id_manfaat"];
        
        $perintah = "DELETE FROM tbl_manfaat WHERE id_manfaat='$id_manfaat'";
        $eksekusi = mysqli_query($connect, $perintah);
        $cek = mysqli_affected_rows($connect);
        
        if($cek>0){
            $response["kode"] = 1;
            $response["pesan"] = "Sukses Menghapus Data";
        }
        else{
            $response["kode"] = 0;
            $response["pesan"] = "Ada Kesalahan. Gagal Menghapus Data";
        }
    }
    else{
        $response["kode"] = 0;
        $response["pesan"] = "Tidak Ada Data yang Dihapus";
    }
    
    echo json_encode($response);
    mysqli_close($connect);
?>