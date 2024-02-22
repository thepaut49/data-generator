import { isAPIError } from "./InterfaceUtils";

export function throwError(error: any) {
  if (error.data && isAPIError(error.data)) {
    throw error.data;
  } else {
    throw error;
  }
}

type ActionFunction<T> = (...args: any[]) => T;

export function handleError<T>(
  action: ActionFunction<T>,
  signIn: any,
  error: any,
  retry: boolean,
  ...params: any[]
): void {
  // En cas d'échec du retry, on envoie une erreur
  if (retry) {
    throwError(error);
  } else {
    if (error.data && isAPIError(error.data)) {
      if (error.data.status === 401) {
        signIn("keycloak");
        action([...params, true]);
      } else {
        throw error.data;
      }
    } else {
      throw error;
    }
  }
}

export function handleErrorFromStore(errorStore: any) {
  if (isAPIError(errorStore)) {
    throw createError({
      statusCode: errorStore.status,
      statusMessage: JSON.stringify(errorStore),
    });
  } else {
    throw createError({
      statusCode: 500,
      statusMessage: JSON.stringify(errorStore),
    });
  }
}
