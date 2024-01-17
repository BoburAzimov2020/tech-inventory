import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IShelfType } from 'app/shared/model/shelf-type.model';
import { getEntities as getShelfTypes } from 'app/entities/shelf-type/shelf-type.reducer';
import { IShelf } from 'app/shared/model/shelf.model';
import { getEntity, updateEntity, createEntity, reset } from './shelf.reducer';

export const ShelfUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const shelfTypes = useAppSelector(state => state.shelfType.entities);
  const shelfEntity = useAppSelector(state => state.shelf.entity);
  const loading = useAppSelector(state => state.shelf.loading);
  const updating = useAppSelector(state => state.shelf.updating);
  const updateSuccess = useAppSelector(state => state.shelf.updateSuccess);

  const handleClose = () => {
    navigate('/shelf' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getShelfTypes({}));
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
      ...shelfEntity,
      ...values,
      shelfType: shelfTypes.find(it => it.id.toString() === values.shelfType.toString()),
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
          ...shelfEntity,
          shelfType: shelfEntity?.shelfType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.shelf.home.createOrEditLabel" data-cy="ShelfCreateUpdateHeading">
            Создать или отредактировать Shelf
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="shelf-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Serial Number"
                id="shelf-serialNumber"
                name="serialNumber"
                data-cy="serialNumber"
                type="text"
                validate={{
                  maxLength: { value: 64, message: 'Это поле не может быть длинее, чем 64 символов.' },
                }}
              />
              <UncontrolledTooltip target="serialNumberLabel">Seriyasi</UncontrolledTooltip>
              <ValidatedField
                label="Digit Number"
                id="shelf-digitNumber"
                name="digitNumber"
                data-cy="digitNumber"
                type="text"
                validate={{
                  maxLength: { value: 64, message: 'Это поле не может быть длинее, чем 64 символов.' },
                }}
              />
              <UncontrolledTooltip target="digitNumberLabel">Raqami</UncontrolledTooltip>
              <ValidatedField
                label="Info"
                id="shelf-info"
                name="info"
                data-cy="info"
                type="text"
                validate={{
                  maxLength: { value: 1024, message: 'Это поле не может быть длинее, чем 1024 символов.' },
                }}
              />
              <ValidatedField id="shelf-shelfType" name="shelfType" data-cy="shelfType" label="Shelf Type" type="select">
                <option value="" key="0" />
                {shelfTypes
                  ? shelfTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/shelf" replace color="info">
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

export default ShelfUpdate;
