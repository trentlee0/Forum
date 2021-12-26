import jutil from "jutils-src";

export function formatDate(timestamp) {
  return jutil.formatDate(new Date(timestamp), "YYYY-MM-DD HH:ii");
}

export function formatBirthday(timestamp) {
  return jutil.formatDate(new Date(timestamp), 'YYYY-MM-DD')
}
