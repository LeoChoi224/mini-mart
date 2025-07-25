function formatNumberWithComma(number) {
    return Number(number).toLocaleString('ko-KR');
}

// ✅ 페이지 로드시 자동으로 .product-price 적용
document.addEventListener("DOMContentLoaded", () => {
    const priceElements = document.querySelectorAll(".product-price");
    priceElements.forEach(el => {
        const price = el.dataset.price;
        if (price) {
            el.innerText = formatNumberWithComma(price) + "원";
        }
    });
});