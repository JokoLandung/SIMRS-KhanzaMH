<?php
    include '../conf/conf.php';
    include '../phpqrcode/qrlib.php'; 
    
    $spribpjs = validTeks(str_replace("_"," ",$_GET['spribpjs']));
    if(isset($spribpjs)){
        $PNG_TEMP_DIR   = dirname(__FILE__).DIRECTORY_SEPARATOR.'temp'.DIRECTORY_SEPARATOR;
        $PNG_WEB_DIR    = 'temp/';
        if (!file_exists($PNG_TEMP_DIR)) mkdir($PNG_TEMP_DIR);
        $filename              = $PNG_TEMP_DIR.$spribpjs.'.png';
        $errorCorrectionLevel  = 'L';
        $matrixPointSize       = 4;
        $setting               = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));
        QRcode::png("".getOne("select no_surat from bridging_surat_pri_bpjs  where no_surat='$spribpjs'"), $filename, $errorCorrectionLevel, $matrixPointSize, 2);
    }   
?>  
