document.addEventListener("DOMContentLoaded", function() {
    // Quando la pagina è completamente caricata
    document.body.style.opacity = 1; // Imposta l'opacità al valore completo
    var header = document.querySelector("header"); // Seleziona header
    var footer = document.querySelector("footer"); // Seleziona footer
    header.style.opacity = 1; // opacità dell'header al valore completo
    footer.style.opacity = 1; // opacità del footer al valore completo

});
$(document).ready(function(){
    $('.navbar-toggler').click(function(){
      $('#navbarNav').toggleClass('show');
    });
  });
// Lista degli URL delle pagine da precaricare
var pagesToPreload = [
    '/admin/artists',
    '/admin/deleteArtist/',
    '/admin/formNewArtist',
    '/admin/artist/',
    '/admin/updateAuthors/',
    '/admin/comics',
    '/admin/comic/',
    '/admin/formNewComic',
    '/admin/formNewExhibition',
    '/admin/updateComic/',
    '/admin/updateAuthors/',
    '/admin/exhibitions',
    '/admin/formUpdateExhibition/',
    '/admin/formUpdateComic/',
    '/admin/searchExhibitions',
    '/admin/searchArtists',
    '/success',
    '/'
    // Aggiungi qui altri URL delle pagine
];

// Cicla attraverso la lista e precarica le pagine
pagesToPreload.forEach(function(url) {
    fetch(url);
});