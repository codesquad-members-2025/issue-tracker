export default function makeFormData(dataObj, files) {
  const formData = new FormData();
  formData.append('data', JSON.stringify({ data: dataObj, files }));
}
