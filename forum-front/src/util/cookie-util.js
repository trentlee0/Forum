export function setCookie(name, value, day = 1) {
  let date = new Date();
  date.setDate(date.getDate() + day);
  document.cookie = name + "=" + value + "; expires=" + date;
}

export function getCookie(name) {
  let array = document.cookie.split("; ");
  for (let i = 0; i < array.length; i++) {
    let keyValue = array[i].split("=");
    if (keyValue[0] === name) {
      return keyValue[1];
    }
  }
}

export function getToken() {
  return getCookie('token')
}

export function removeCookie(name) {
  setCookie(name, 1, -1);
}
