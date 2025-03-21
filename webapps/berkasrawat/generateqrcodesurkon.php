<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $suratkontrolbpjs = validTeks(str_replace("_"," ",$_GET['suratkontrolbpjs']));
    if(isset($suratkontrolbpjs)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.$suratkontrolbpjs.'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        $setting               = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));
        QRcode::png("".getOne("select bridging_surat_kontrol_bpjs.no_surat from bridging_surat_kontrol_bpjs where no_surat='$suratkontrolbpjs'"), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  
