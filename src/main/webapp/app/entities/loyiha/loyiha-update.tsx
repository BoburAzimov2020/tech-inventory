import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IObjectTasnifi } from 'app/shared/model/object-tasnifi.model';
import { getEntities as getObjectTasnifis } from 'app/entities/object-tasnifi/object-tasnifi.reducer';
import { ILoyiha } from 'app/shared/model/loyiha.model';
import { getEntity, updateEntity, createEntity, reset } from './loyiha.reducer';

export const LoyihaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const objectTasnifis = useAppSelector(state => state.objectTasnifi.entities);
  const loyihaEntity = useAppSelector(state => state.loyiha.entity);
  const loading = useAppSelector(state => state.loyiha.loading);
  const updating = useAppSelector(state => state.loyiha.updating);
  const updateSuccess = useAppSelector(state => state.loyiha.updateSuccess);

  const handleClose = () => {
    navigate('/loyiha' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getObjectTasnifis({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...loyihaEntity,
      ...values,
      objectTasnifi: objectTasnifis.find(it => it.id.toString() === values.objectTasnifi.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...loyihaEntity,
          objectTasnifi: loyihaEntity?.objectTasnifi?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.loyiha.home.createOrEditLabel" data-cy="LoyihaCreateUpdateHeading">
            Создать или отредактировать Loyiha
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="loyiha-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="loyiha-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Это поле обязательно к заполнению.' },
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField id="loyiha-objectTasnifi" name="objectTasnifi" data-cy="objectTasnifi" label="Object Tasnifi" type="select">
                <option value="" key="0" />
                {objectTasnifis
                  ? objectTasnifis.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/loyiha" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Назад</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Сохранить
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default LoyihaUpdate;
