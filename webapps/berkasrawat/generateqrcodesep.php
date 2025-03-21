<?php

// Include necessary files
include '../conf/conf.php';
include '../phpqrcode/qrlib.php';

// Generate QR code for SEP (Surat Elektronik Peserta)
if (isset($_GET['sepbpjs'])) {
    $sepbpjs = validTeks(str_replace("_", " ", $_GET['sepbpjs']));

    $PNG_TEMP_DIR = dirname(__FILE__) . DIRECTORY_SEPARATOR . 'temp' . DIRECTORY_SEPARATOR;
    $PNG_WEB_DIR = 'temp/';

    if (!file_exists($PNG_TEMP_DIR)) {
        mkdir($PNG_TEMP_DIR); // Create temporary directory if it doesn't exist
    }

    $filename = $PNG_TEMP_DIR . $sepbpjs . '.png';
    $errorCorrectionLevel = 'L';
    $matrixPointSize = 4;

    $setting = mysqli_fetch_array(bukaquery("select nama_instansi,kabupaten from setting"));

    $noSep = getOne("select no_sep from bridging_sep where no_sep='$sepbpjs'"); // Retrieve SEP number
    QRcode::png($noSep, $filename, $errorCorrectionLevel, $matrixPointSize, 2);
}

?>

