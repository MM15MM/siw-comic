
  // Mostra lo spinner quando inizia il caricamento
  function showSpinner() {
    document.getElementById("loadingSpinner").style.display = "block";
  }

  // Nascondi lo spinner quando il caricamento è completato
  function hideSpinner() {
    document.getElementById("loadingSpinner").style.display = "none";
  }

  
  function fetchData() {
    // Mostra lo spinner 
    showSpinner();


    // Quando il caricamento è completato, nascondi lo spinner
    setTimeout(hideSpinner, 2000); // nascondi lo spinner dopo 3 secondi
  }

  //  mostra lo spinner al caricamento della pagina
  fetchData();