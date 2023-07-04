<?php
    require("koneksi.php");
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        $id_kalori = $_POST["id_kalori"];
        
        $perintah = "DELETE FROM tbl_kalori WHERE id_kalori='$id_kalori'";
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