import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILoyiha } from 'app/shared/model/loyiha.model';
import { getEntities as getLoyihas } from 'app/entities/loyiha/loyiha.reducer';
import { IBuyurtmaRaqam } from 'app/shared/model/buyurtma-raqam.model';
import { getEntity, updateEntity, createEntity, reset } from './buyurtma-raqam.reducer';

export const BuyurtmaRaqamUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const loyihas = useAppSelector(state => state.loyiha.entities);
  const buyurtmaRaqamEntity = useAppSelector(state => state.buyurtmaRaqam.entity);
  const loading = useAppSelector(state => state.buyurtmaRaqam.loading);
  const updating = useAppSelector(state => state.buyurtmaRaqam.updating);
  const updateSuccess = useAppSelector(state => state.buyurtmaRaqam.updateSuccess);

  const handleClose = () => {
    navigate('/buyurtma-raqam' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLoyihas({}));
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
      ...buyurtmaRaqamEntity,
      ...values,
      loyiha: loyihas.find(it => it.id.toString() === values.loyiha.toString()),
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
          ...buyurtmaRaqamEntity,
          loyiha: buyurtmaRaqamEntity?.loyiha?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.buyurtmaRaqam.home.createOrEditLabel" data-cy="BuyurtmaRaqamCreateUpdateHeading">
            Создать или отредактировать Buyurtma Raqam
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="buyurtma-raqam-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Name" id="buyurtma-raqam-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label="Number Of Order"
                id="buyurtma-raqam-numberOfOrder"
                name="numberOfOrder"
                data-cy="numberOfOrder"
                type="text"
              />
              <ValidatedField id="buyurtma-raqam-loyiha" name="loyiha" data-cy="loyiha" label="Loyiha" type="select">
                <option value="" key="0" />
                {loyihas
                  ? loyihas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/buyurtma-raqam" replace color="info">
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

export default BuyurtmaRaqamUpdate;
