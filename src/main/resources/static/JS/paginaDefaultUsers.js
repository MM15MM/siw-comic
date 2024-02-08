document.addEventListener("DOMContentLoaded", function() {
    // Quando la pagina è completamente caricata
    document.body.style.opacity = 1; // Imposta l'opacità al valore completo
    var header = document.querySelector("header"); // Seleziona l'elemento header
    var footer = document.querySelector("footer"); // Seleziona l'elemento footer
    header.style.opacity = 1; // Imposta l'opacità dell'header al valore completo
    footer.style.opacity = 1; // Imposta l'opacità del footer al valore completo

});

// Lista degli URL delle pagine da precaricare
var pagesToPreload = [
    '/artist/',
    '/artists',
    '/comic',
    '/favorites',
    '/index',
    '/success',
    '/register',
    '/login',
    '/comic/',
    '/comics',
    '/searchExhibitions',
    '/exhibitions',
    '/searchArtists',
    '/'
    // Aggiungi qui altri URL delle pagine
];

// Cicla attraverso la lista e precarica le pagine
pagesToPreload.forEach(function(url) {
    fetch(url);
});