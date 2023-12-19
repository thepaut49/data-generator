export function useEntityNavigationButtons(basePath: string, urlParam: string) {
  const historyButton = {
    text: "Historique",
    route: basePath + urlParam + "/history",
  };

  const detailsButton = {
    text: "Détails",
    route: basePath + urlParam,
  };

  const modifyButton = {
    text: "Modifier",
    route: basePath + urlParam + "/update",
  };

  return [detailsButton, modifyButton, historyButton];
}
