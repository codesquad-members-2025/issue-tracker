export default function getFormData(dataObj, files) {
  const formData = new FormData();
  formData.append('data', JSON.stringify({ data: dataObj }));
  formData.append('files', files || null);
  return formData;
}
