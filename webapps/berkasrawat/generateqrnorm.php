<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $no_rkm_medis = validTeks(str_replace("_"," ",$_GET['no_rkm_medis']));
    if(isset($no_rkm_medis)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.$no_rkm_medis.'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        $setting               = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));
        QRcode::png("".getOne("select no_rkm_medis from pasien  where no_rkm_medis='$no_rkm_medis'"), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  
