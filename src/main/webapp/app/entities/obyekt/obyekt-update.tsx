import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBuyurtmaRaqam } from 'app/shared/model/buyurtma-raqam.model';
import { getEntities as getBuyurtmaRaqams } from 'app/entities/buyurtma-raqam/buyurtma-raqam.reducer';
import { IObyekt } from 'app/shared/model/obyekt.model';
import { getEntity, updateEntity, createEntity, reset } from './obyekt.reducer';

export const ObyektUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buyurtmaRaqams = useAppSelector(state => state.buyurtmaRaqam.entities);
  const obyektEntity = useAppSelector(state => state.obyekt.entity);
  const loading = useAppSelector(state => state.obyekt.loading);
  const updating = useAppSelector(state => state.obyekt.updating);
  const updateSuccess = useAppSelector(state => state.obyekt.updateSuccess);

  const handleClose = () => {
    navigate('/obyekt' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBuyurtmaRaqams({}));
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
      ...obyektEntity,
      ...values,
      buyurtmaRaqam: buyurtmaRaqams.find(it => it.id.toString() === values.buyurtmaRaqam.toString()),
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
          ...obyektEntity,
          buyurtmaRaqam: obyektEntity?.buyurtmaRaqam?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.obyekt.home.createOrEditLabel" data-cy="ObyektCreateUpdateHeading">
            Translation missing for techInventoryApp.obyekt.home.createOrEditLabel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="obyekt-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="obyekt-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Это поле обязательно к заполнению.' },
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField label="Home" id="obyekt-home" name="home" data-cy="home" type="text" />
              <ValidatedField label="Street" id="obyekt-street" name="street" data-cy="street" type="text" />
              <ValidatedField label="Latitude" id="obyekt-latitude" name="latitude" data-cy="latitude" type="text" />
              <ValidatedField label="Longitude" id="obyekt-longitude" name="longitude" data-cy="longitude" type="text" />
              <ValidatedField
                label="Info"
                id="obyekt-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="obyekt-buyurtmaRaqam" name="buyurtmaRaqam" data-cy="buyurtmaRaqam" label="Buyurtma Raqam" type="select">
                <option value="" key="0" />
                {buyurtmaRaqams
                  ? buyurtmaRaqams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/obyekt" replace color="info">
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

export default ObyektUpdate;
