












const list = document.querySelector("#active-carousel");
const items = list.querySelectorAll("li");
const productList = document.querySelector("#active-carousel-product");
const itemsProduct = productList.querySelectorAll("div");
for (const item of items) {
  item.addEventListener("click", function() {
    for (const i of items) {
      i.classList.remove("active");
    }
    this.classList.add("active");
    const index = this.get
  });
}
for (const item of items) {
    item.addEventListener("click", function() {
      for (const i of itemsProduct) {
        i.classList.remove("active");
      }
      this.classList.add("active");
    });
  }