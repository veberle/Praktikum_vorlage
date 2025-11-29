<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-7xl mx-auto p-6">
  <div class="flex items-center justify-between mb-6">
    <h1 class="text-2xl font-semibold text-gray-800">Skillmatrix</h1>
  </div>

  <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
    <!-- Linke Spalte: Mitarbeiter nach Abteilung -->
    <section class="rounded-lg border border-gray-200 bg-white p-4 shadow-sm">
      <h2 class="mb-3 text-lg font-medium text-gray-700">Mitarbeiter</h2>
      <div class="space-y-4 max-h-[28rem] overflow-auto pr-1">
        <#list deptGroups![] as g>
          <fieldset class="border border-gray-200 rounded-md">
            <legend class="px-2 text-sm font-semibold text-gray-700">${g.name}</legend>
            <div class="p-2 space-y-1">
              <#if (g.employees?size == 0)>
                <div class="text-xs text-gray-400">Keine Mitarbeiter</div>
              <#else>
                <#list g.employees as e>
                  <label class="flex items-center gap-2 text-sm">
                    <input type="checkbox" class="h-4 w-4 rounded border border-gray-300 text-blue-600 focus:ring-blue-500 employee-chk" value="${e.id}" />
                    <span>${e.name}</span>
                  </label>
                </#list>
              </#if>
            </div>
          </fieldset>
        </#list>
      </div>
    </section>

    <!-- Mitte: Radar-Chart -->
    <section class="rounded-lg border border-gray-200 bg-white p-4 shadow-sm">
      <h2 class="mb-4 text-lg font-medium text-gray-700">${title!"Radar-Diagramm"}</h2>
      <canvas id="skillRadar" height="280"></canvas>
      <p id="chart-hint" class="mt-3 text-sm text-gray-500">Bitte Mitarbeiter und Skills auswählen.</p>
    </section>

    <!-- Rechte Spalte: Skills nach Kategorie -->
    <section class="rounded-lg border border-gray-200 bg-white p-4 shadow-sm">
      <h2 class="mb-3 text-lg font-medium text-gray-700">Skills</h2>
      <div class="space-y-4 max-h-[28rem] overflow-auto pr-1">
        <#list categoryGroups![] as c>
          <fieldset class="border border-gray-200 rounded-md">
            <legend class="px-2 text-sm font-semibold text-gray-700">${c.name}</legend>
            <div class="p-2 space-y-1">
              <#if (c.skills?size == 0)>
                <div class="text-xs text-gray-400">Keine Skills</div>
              <#else>
                <#list c.skills as s>
                  <label class="flex items-center gap-2 text-sm">
                    <input type="checkbox" class="h-4 w-4 rounded border border-gray-300 text-blue-600 focus:ring-blue-500 skill-chk" value="${s.id}" />
                    <span>${s.name}</span>
                  </label>
                </#list>
              </#if>
            </div>
          </fieldset>
        </#list>
      </div>
    </section>
  </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
<script>
  (function() {
    const ctx = document.getElementById('skillRadar');
    const hint = document.getElementById('chart-hint');
    if (!ctx) return;

    let chart;

    function colors(i) {
      const palette = [
        'rgb(37,99,235)',      // blue-600
        'rgb(16,185,129)',     // emerald-500
        'rgb(244,114,182)',    // pink-400
        'rgb(234,179,8)',      // yellow-500
        'rgb(99,102,241)',     // indigo-500
        'rgb(239,68,68)',      // red-500
        'rgb(59,130,246)',     // blue-500
        'rgb(20,184,166)'      // teal-500
      ];
      return palette[i % palette.length];
    }

    function getSelected(selector) {
      return Array.from(document.querySelectorAll(selector))
        .filter(cb => cb.checked)
        .map(cb => cb.value);
    }

    async function updateChart() {
      const employeeIds = getSelected('.employee-chk');
      const skillIds = getSelected('.skill-chk');

      if (employeeIds.length === 0 || skillIds.length === 0) {
        if (chart) { chart.destroy(); chart = undefined; }
        if (hint) hint.classList.remove('hidden');
        return;
      }
      if (hint) hint.classList.add('hidden');

      const params = new URLSearchParams();
      params.append('employeeIds', employeeIds.join(','));
      params.append('skillIds', skillIds.join(','));

      const res = await fetch('/skillmatrix/data?' + params.toString(), { headers: { 'Accept': 'application/json' } });
      if (!res.ok) return;
      const json = await res.json();

      const datasets = (json.datasets || []).map((ds, i) => {
        const col = colors(i);
        return {
          label: ds.label || ('Mitarbeiter ' + (i+1)),
          data: ds.data || [],
          fill: true,
          backgroundColor: col.replace('rgb', 'rgba').replace(')', ', 0.2)'),
          borderColor: col,
          pointBackgroundColor: col,
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: col
        };
      });

      if (chart) chart.destroy();
      chart = new Chart(ctx, {
        type: 'radar',
        data: {
          labels: json.labels || [],
          datasets
        },
        options: {
          responsive: true,
          maintainAspectRatio: true,
          plugins: { legend: { display: true } },
          scales: { r: { beginAtZero: true, min: 0, max: 100, ticks: { stepSize: 20 } } }
        }
      });
    }

    // Event Listener für Checkboxen
    document.addEventListener('change', (e) => {
      if (e.target.matches('.employee-chk') || e.target.matches('.skill-chk')) {
        updateChart();
      }
    });

    // Optional: keine Vorauswahl, Chart-Hinweis sichtbar
  })();
</script>

<#include "../_footer.ftl">
