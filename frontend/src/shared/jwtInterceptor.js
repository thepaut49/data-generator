import axios from "axios";
import { useUserStore } from "../stores/UserStore";

const jwtInterceptor = axios.create({});
jwtInterceptor.defaults.headers.common["Authorization"] = "";

jwtInterceptor.interceptors.request.use((config) => {
  const userStore = useUserStore();
  const token = userStore.getToken;
  if (token == null) {
    return config;
  }

  config.headers["Authorization"] = `bearer ${token}`;
  return config;
});

jwtInterceptor.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response.status === 401) {
      const userStore = useUserStore();
      if (userStore.isRefreshTokenAlive) {
        userStore.refreshToken;
        error.config.headers["Authorization"] = `bearer ${userStore.getToken}`;
      } else {
        userStore
          .login(window.location.href)
          .then((auth) => {
            if (!auth) {
              window.location.reload;
            } else {
              error.config.headers[
                "Authorization"
              ] = `bearer ${userStore.getToken}`;
            }
          })
          .catch(() => {
            console.error("Authenticated Failed");
          });
      }
      return axios(error.config);
    } else {
      return Promise.reject(error);
    }
  }
);

export default jwtInterceptor;
