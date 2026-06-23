document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("[data-tabs]").forEach((tabRoot) => {
    const tabs = tabRoot.querySelectorAll("[data-tab]");
    const panels = tabRoot.querySelectorAll("[data-panel]");

    tabs.forEach((tab) => {
      tab.addEventListener("click", () => {
        tabs.forEach((item) => item.classList.remove("active"));
        panels.forEach((panel) => panel.classList.remove("active"));
        tab.classList.add("active");
        tabRoot.querySelector(`[data-panel="${tab.dataset.tab}"]`)?.classList.add("active");
      });
    });
  });

  document.querySelectorAll(".filter-control").forEach((control) => {
    control.addEventListener("change", () => {
      const list = document.querySelector(control.dataset.filterTarget);
      if (!list) return;

      list.querySelectorAll("[data-status]").forEach((item) => {
        const visible = control.value === "ALL" || item.dataset.status === control.value;
        item.hidden = !visible;
      });
    });
  });

  document.querySelectorAll("textarea[maxlength]").forEach((textarea) => {
    const counter = textarea.closest("label")?.querySelector(".char-count");
    const update = () => {
      if (counter) {
        counter.textContent = `${textarea.value.length} / ${textarea.maxLength}`;
      }
    };
    textarea.addEventListener("input", update);
    update();
  });

  document.querySelectorAll(".request-item, .stat-card, .tool-panel").forEach((item, index) => {
    item.style.animationDelay = `${Math.min(index * 60, 420)}ms`;
    item.classList.add("rise-in");
  });
});
