import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ObjectTasnifi from './object-tasnifi';
import ObjectTasnifiDetail from './object-tasnifi-detail';
import ObjectTasnifiUpdate from './object-tasnifi-update';
import ObjectTasnifiDeleteDialog from './object-tasnifi-delete-dialog';

const ObjectTasnifiRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ObjectTasnifi />} />
    <Route path="new" element={<ObjectTasnifiUpdate />} />
    <Route path=":id">
      <Route index element={<ObjectTasnifiDetail />} />
      <Route path="edit" element={<ObjectTasnifiUpdate />} />
      <Route path="delete" element={<ObjectTasnifiDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ObjectTasnifiRoutes;
