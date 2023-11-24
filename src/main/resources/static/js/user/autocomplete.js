function addOptionsFromData(data) {
  var f = JSON.stringify(data);
  var w = f.replace(/\[/g, "");
  w = w.replace(/\]/g, "");
  w = "<option value=" + w;
  w = w.replace(/,/g, "><option value=");
  w = w + ">";
  document.getElementById("output").innerHTML = w;

  var options = document.getElementById("output").childNodes;
  for (var i = 0; i < options.length; i++) {
    if (i >= 8) {
      options[i].value = "";
    }
  }
}

let Searcher = (() => {
  let escapeRegExp = /[\-#$\^*()+\[\]{}|\\,.?\s]/g;
  let escapeReg = (reg) => reg.replace(escapeRegExp, "\\$&");
  let groupLeft = "",
    groupRight = "";
  let groupReg = new RegExp(escapeReg(groupRight + groupLeft), "g");
  let groupExtractReg = new RegExp(
    "(" + escapeReg(groupLeft) + "[\\s\\S]+?" + escapeReg(groupRight) + ")",
    "g"
  );
  let findMax = (str, keyword) => {
    let max = 0;
    keyword = groupLeft + keyword + groupRight;
    str.replace(groupExtractReg, (m) => {
      if (keyword == m) {
        max = Number.MAX_SAFE_INTEGER;
      } else if (m.length > max) {
        max = m.length;
      }
    });
    //   console.log("key::" + max);
    return max;
  };
  let keyReg = (key) => {
    let src = ["(.*?)("];
    let ks = key.split("");
    if (ks.length) {
      while (ks.length) {
        src.push(escapeReg(ks.shift()), ")(.*?)(");
      }
      src.pop();
    }
    src.push(")(.*?)");
    src = src.join("");
    let reg = new RegExp(src, "i");
    let replacer = [];
    let start = key.length;
    let begin = 1;
    while (start > 0) {
      start--;
      replacer.push("$", begin, groupLeft + "$", begin + 1, groupRight);
      begin += 2;
    }
    replacer.push("$", begin);

    info = {
      regexp: reg,
      replacement: replacer.join(""),
    };
    return info;
  };

  return {
    searchWikipediaJSONP(keyword) {
      // Tạo một callback ngẫu nhiên để tránh xung đột
      const callbackName =
        "jsonpCallback_" + Math.round(100000 * Math.random());

      // Thêm hàm callback vào global scope
      window[callbackName] = (data) => {
        // Xử lý dữ liệu và thực hiện tìm kiếm
        let kr = keyReg(keyword);
        let result = [];
        for (let e of data[1]) {
          if (kr.regexp.test(e)) {
            result.push(
              e.replace(kr.regexp, kr.replacement).replace(groupReg, "")
            );
          }
        }
        //   console.log("key::" + result);

        // Sắp xếp kết quả dựa trên mức độ phù hợp với keyword
        result = result.sort(
          (a, b) => findMax(b, keyword) - findMax(a, keyword)
        );

        // Chuyển đổi kết quả thành một mảng chuỗi
        result = result.map((el) => `${el}`);

        console.log(`result::::`, result);
        return result;

        // Sau khi xử lý xong, xóa hàm callback khỏi global scope
        delete window[callbackName];
      };

      // Tạo và thêm thẻ script để gọi API Wikipedia với callback
      const scriptElement = document.createElement("script");
      scriptElement.src = `https://vi.wikipedia.org/w/api.php?action=opensearch&limit=7&format=json&search=${keyword}&callback=addOptionsFromData`;
      document.body.appendChild(scriptElement);
    },
  };
})();

function getanswer() {
  const qurybox = document.getElementById("search");
  const output = document.getElementById("output");
  // Gọi hàm searchWikipediaJSONP với giá trị của input và keyword
  setTimeout(() => {
    // Gọi hàm searchWikipediaJSONP với giá trị của input và keyword
    Searcher.searchWikipediaJSONP(qurybox.value);
  }, 1000);
}
