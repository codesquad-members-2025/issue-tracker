import { useSearchParams } from 'react-router-dom';

export default function useQueryParams() {
  const [searchParams, setSearchParams] = useSearchParams();
  function editParams(newParams) {
    const paramsObject = Object.fromEntries(Array.from(searchParams.entries()));
    Object.entries(newParams).forEach(([key, value]) => {
      if (value === undefined || value === null) {
        delete paramsObject[key];
      } else {
        paramsObject[key] = value;
      }
    });
    setSearchParams(paramsObject);
  }

  return editParams;
}
