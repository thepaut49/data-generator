type ElementType = SampleData | SampleDataCategory;

export function updateElement(
  elementToUpdate: ElementType,
  elements: ElementType[]
): ElementType[] {
  const index = elements.findIndex(
    (h: ElementType) => h.id === elementToUpdate.id
  );
  return [...elements.splice(index, 1, elementToUpdate)];
}
