import { API } from '@/shared/constants/api';

export default async function uploadToS3(file: File): Promise<string> {
  const res = await fetch(`${API.S3_PRESIGNED_URL}?filename=${file.name}`);
  if (!res.ok) throw new Error('Failed to get presigned URL');

  const presignedUrl = await res.text();

  const uploadRes = await fetch(presignedUrl, {
    method: 'PUT',
    headers: {
      'Content-Type': file.type,
    },
    body: file,
  });

  if (!uploadRes.ok) throw new Error('Failed to upload file to S3');

  return presignedUrl.split('?')[0];
}
