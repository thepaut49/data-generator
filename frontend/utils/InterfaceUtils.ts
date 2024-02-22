export function isAPIError(obj: any): obj is APIError {
  // Créer une instance de l'interface et récupérer ses propriétés
  const properties = Object.getOwnPropertyNames(obj);
  const apiErrorProperties = [
    "code",
    "message",
    "path",
    "timestamp",
    "status",
    "apiSubErrors",
  ];
  const propertyIsInAPiError = (value: string) =>
    apiErrorProperties.includes(value);
  return properties.every(propertyIsInAPiError);
}
