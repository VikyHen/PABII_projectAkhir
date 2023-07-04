<?php
    require("koneksi.php");
    $perintah = "SELECT * FROM tbl_kalori";
    $eksekusi = mysqli_query($connect, $perintah);
    
    $cek = mysqli_affected_rows($connect);
    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Tersedia";
        $response["jumlah_kalori"] = 0;
        $response["data"] = array();
        
        // Menghitung jumlah kalori
        $totalKalori = 0;
        while($get = mysqli_fetch_object($eksekusi)){
            $var = array(
                "id_kalori" => $get->id_kalori,
                "nama" => $get->nama,
                "gambar" => $get->gambar,
                "kalori" => $get->kalori,
            );
            $response["data"][] = $var;
            
            // Menambahkan kalori ke totalKalori
            $totalKalori += $get->kalori;
        }
        
        // Menyimpan jumlah kalori ke dalam respons
        $response["jumlah_kalori"] = $totalKalori;
    }
    else{
        $response["kode"] = 0;
        $response["pesan"] = "Data Tidak Tersedia";
    }
    
    echo json_encode($response);
    mysqli_close($connect);
?>
