document.addEventListener('click', (e) => { 
    if (e.target.matches('a')) {
      e.preventDefault();
      e.preventImmediatePropagation(); // might not be necessary
    }
  })