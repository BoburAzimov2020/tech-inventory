import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IObjectTasnifiTuri } from 'app/shared/model/object-tasnifi-turi.model';
import { getEntities as getObjectTasnifiTuris } from 'app/entities/object-tasnifi-turi/object-tasnifi-turi.reducer';
import { IObjectTasnifi } from 'app/shared/model/object-tasnifi.model';
import { getEntity, updateEntity, createEntity, reset } from './object-tasnifi.reducer';

export const ObjectTasnifiUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const objectTasnifiTuris = useAppSelector(state => state.objectTasnifiTuri.entities);
  const objectTasnifiEntity = useAppSelector(state => state.objectTasnifi.entity);
  const loading = useAppSelector(state => state.objectTasnifi.loading);
  const updating = useAppSelector(state => state.objectTasnifi.updating);
  const updateSuccess = useAppSelector(state => state.objectTasnifi.updateSuccess);

  const handleClose = () => {
    navigate('/object-tasnifi' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getObjectTasnifiTuris({}));
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
      ...objectTasnifiEntity,
      ...values,
      bjectTasnifiTuri: objectTasnifiTuris.find(it => it.id.toString() === values.bjectTasnifiTuri.toString()),
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
          ...objectTasnifiEntity,
          bjectTasnifiTuri: objectTasnifiEntity?.bjectTasnifiTuri?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="techInventoryApp.objectTasnifi.home.createOrEditLabel" data-cy="ObjectTasnifiCreateUpdateHeading">
            Создать или отредактировать Object Tasnifi
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
                <ValidatedField name="id" required readOnly id="object-tasnifi-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Name"
                id="object-tasnifi-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Это поле обязательно к заполнению.' },
                  maxLength: { value: 128, message: 'Это поле не может быть длинее, чем 128 символов.' },
                }}
              />
              <ValidatedField
                id="object-tasnifi-bjectTasnifiTuri"
                name="bjectTasnifiTuri"
                data-cy="bjectTasnifiTuri"
                label="Bject Tasnifi Turi"
                type="select"
              >
                <option value="" key="0" />
                {objectTasnifiTuris
                  ? objectTasnifiTuris.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/object-tasnifi" replace color="info">
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

export default ObjectTasnifiUpdate;
