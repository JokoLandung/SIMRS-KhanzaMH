<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $kodepetugas = validTeks(str_replace("_"," ",$_GET['kodepetugas']));
    if(isset($kodepetugas)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.$kodepetugas.'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        $setting               = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));
        QRcode::png("Dikeluarkan di ".$setting["nama_instansi"].", Kabupaten/Kota ".$setting["kabupaten"]."\nDitandatangani secara elektronik oleh ".getOne("select nama from petugas where nip='$kodepetugas'")."\nID ".getOne3("select ifnull(sha1(sidikjari.sidikjari),'".$kodepetugas."') from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik='".$kodepetugas."'",$kodepetugas)."\n".date('d-m-Y'), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  