import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ObjectTasnifiTuri from './object-tasnifi-turi';
import ObjectTasnifiTuriDetail from './object-tasnifi-turi-detail';
import ObjectTasnifiTuriUpdate from './object-tasnifi-turi-update';
import ObjectTasnifiTuriDeleteDialog from './object-tasnifi-turi-delete-dialog';

const ObjectTasnifiTuriRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ObjectTasnifiTuri />} />
    <Route path="new" element={<ObjectTasnifiTuriUpdate />} />
    <Route path=":id">
      <Route index element={<ObjectTasnifiTuriDetail />} />
      <Route path="edit" element={<ObjectTasnifiTuriUpdate />} />
      <Route path="delete" element={<ObjectTasnifiTuriDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ObjectTasnifiTuriRoutes;
