export const parseJwt = (token) => {
  try {
    const base64Payload = token.split(".")[1];
    const jsonPayload = atob(base64Payload);
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error("JWT 파싱 실패:", e);
    return null;
  }
};
