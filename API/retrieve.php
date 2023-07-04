<?php
    require("koneksi.php");
    //$perintah = "SELECT * FROM tbl_food";
    $perintah = "SELECT *
                FROM tbl_food AS f
                LEFT JOIN tbl_manfaat AS m ON f.id = m.food_id";
    $eksekusi = mysqli_query($connect, $perintah);
    
    $cek = mysqli_affected_rows($connect);
    if($cek>0){
        $response["kode"] = 1;
        $response["pesan"] = "Data Tersedia";
        $response["data"] = array();
        
        while($get = mysqli_fetch_object($eksekusi)){
            $var = array(
                "id" => $get->id,
                "nama" => $get->nama,
                //"gambar" => $get->gambar,
                "gambar" => "https://listhealthyfood.000webhostapp.com/images/". $get->gambar,
                "deskripsi" => $get->deskripsi,
                "kalori" => $get->kalori,
                "manfaat" => null
            );
            
            if ($get->id !== null) {
                $manfaat = array(
                    "id_manfaat" => $get->id_manfaat,
                    "food_id" => $get->food_id,
                    "kandungan" => $get->kandungan,
                    "jumlah" => $get->jumlah,
                    "manfaatKandungan" => $get->manfaatKandungan
                );
                
                $var["manfaat"][] = $manfaat;
            }
            
            
            $index = array_search($get->id, array_column($response["data"], "id"));

            if ($index !== false) {
                // Tambahkan manfaat ke data yang sudah ada
                $response["data"][$index]["manfaat"][] = $manfaat;
            } 
            else{
                $response["data"][] = $var;
            }
        }
    }
    else{
        $response["kode"] = 0;
        $response["pesan"] = "Data Tidak Tersedia";
    }
    
    echo json_encode($response);
    mysqli_close($connect);
?>