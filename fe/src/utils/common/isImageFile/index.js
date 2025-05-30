export default function isImageFile(url) {
  return /\.(jpe?g|png|gif|bmp|webp|svg)$/i.test(url);
}
