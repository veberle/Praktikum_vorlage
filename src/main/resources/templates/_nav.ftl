<nav class="bg-white shadow">
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="flex h-14 items-center justify-between">
      <div class="flex items-center gap-6 text-sm">
        <!-- Stammdaten Dropdown (per Klick toggeln) -->
        <div class="relative" id="nav-stammdaten">
          <button
              type="button"
              class="inline-flex items-center gap-1 text-gray-700 hover:text-blue-600 font-medium"
              aria-haspopup="true"
              aria-expanded="false"
              aria-controls="stammdaten-menu"
              data-menu-toggle>
            Stammdaten
            <svg class="w-4 h-4 transition-transform duration-150" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path></svg>
          </button>
          <div
              id="stammdaten-menu"
              role="menu"
              aria-labelledby="stammdaten-button"
              class="hidden absolute left-0 mt-2 w-56 rounded-md border border-gray-200 bg-white shadow-lg z-50">
            <div class="py-1">
              <a href="/departments" class="block px-4 py-2 text-gray-700 hover:bg-gray-200" role="menuitem">Abteilungen</a>
              <a href="/employees" class="block px-4 py-2 text-gray-700 hover:bg-gray-200" role="menuitem">Mitarbeiter</a>
              <a href="/skillcategories" class="block px-4 py-2 text-gray-700 hover:bg-gray-200" role="menuitem">Skill-Kategorien</a>
              <a href="/skills" class="block px-4 py-2 text-gray-700 hover:bg-gray-200" role="menuitem">Skills</a>
              <a href="/employee-skills" class="block px-4 py-2 text-gray-700 hover:bg-gray-200" role="menuitem">Mitarbeiter-Skills</a>
            </div>
          </div>
        </div>

        <!-- Skillmatrix Top-Level Link -->
        <a href="/skillmatrix" class="text-gray-700 hover:text-blue-600 font-medium">Skillmatrix</a>
      </div>
      <div class="text-xs text-gray-400 hidden sm:block">Skillmatrix</div>
    </div>
  </div>

  <!-- Minimaler Toggle-Script (vanilla JS) -->
  <script>
    (function() {
      const container = document.getElementById('nav-stammdaten');
      if (!container) return;

      const toggleBtn = container.querySelector('[data-menu-toggle]');
      const menu = container.querySelector('#stammdaten-menu');
      const chevron = toggleBtn.querySelector('svg');

      function openMenu() {
        menu.classList.remove('hidden');
        toggleBtn.setAttribute('aria-expanded', 'true');
        if (chevron) chevron.classList.add('rotate-180');
      }

      function closeMenu() {
        menu.classList.add('hidden');
        toggleBtn.setAttribute('aria-expanded', 'false');
        if (chevron) chevron.classList.remove('rotate-180');
      }

      function toggleMenu() {
        if (menu.classList.contains('hidden')) {
          openMenu();
        } else {
          closeMenu();
        }
      }

      toggleBtn.addEventListener('click', function(e) {
        e.preventDefault();
        toggleMenu();
      });

      // Schließen bei Klick außerhalb
      document.addEventListener('click', function(event) {
        if (!container.contains(event.target)) {
          closeMenu();
        }
      });

      // Schließen mit Escape
      document.addEventListener('keydown', function(event) {
        if (event.key === 'Escape') {
          closeMenu();
          toggleBtn.focus();
        }
      });
    })();
  </script>
</nav>
